import http from 'k6/http';
import { sleep, check } from 'k6';


// k6 run --out influxdb=http://localhost:8086/testdb query_test.js

export const options = {
  scenarios: {
    find_max_log_throughput: {
      executor: 'constant-arrival-rate',
      rate: 500, // 초당 요청 수
      timeUnit: '1s',
      duration: '10s',
      preAllocatedVUs: 200,
      maxVUs: 1000,
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<3000'],
  },
};


function formatDate(date) {
  return date.toISOString();
}

export default function () {
  const url = 'http://localhost:8080/api/query-logs';

  const now = new Date();

  const queries = Array.from({ length: 100 }, (_, i) => {
    const createdAt = new Date(now.getTime() + i * 1000); // 1초씩 증가
    return {
      methodName: `methodName${i}`,
      createdAt: formatDate(createdAt),
      sqlQuery: `SELECT * FROM table${i}`,
      executionPlan: `executionPlan${i}`,
      duration: Math.floor(Math.random() * 1000),
    };
  });

  const payload = JSON.stringify(queries);

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'X-API-KEY': 'c4db0348054449be9ee75da4e8d98729',
    },
  };

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}