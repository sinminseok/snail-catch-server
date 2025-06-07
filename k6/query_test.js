import http from 'k6/http';
import { sleep, check } from 'k6';


// k6 run --out influxdb=http://localhost:8086/k6 --threshold-warnings query_test.js

export const options = {
  scenarios: {
    constant_rate_test: {
      executor: 'constant-arrival-rate',
      rate: 5000,              // 초당 200건부터 시작
      timeUnit: '1s',
      duration: '10s',
      preAllocatedVUs: 100,
      maxVUs: 1000,
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],     // 실패율 1% 미만
    http_req_duration: ['p(95)<3000'],  // 95% 요청 3초 이내 응답
  },
};

export default function () {
  const url = 'http://localhost:8080/api/query-logs/all';

  const queries = Array.from({ length: 10 }, (_, i) => ({
    methodName: `methodName${i}`,
    sqlQuery: `SELECT * FROM table${i}`,
    executionPlan: `executionPlan${i}`,
    duration: Math.floor(Math.random() * 1000), // 0 ~ 999 랜덤 duration
  }));

  const payload = JSON.stringify(queries);

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'X-API-KEY': '3ae32a7910c145d4872f4e5e21c6cb64',
    },
  };

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}

//export default function () {
//  const url = 'http://localhost:8080/api/query-logs';
//  const payload = JSON.stringify({
//    methodName: "methodname",
//    sqlQuery: "SELECT * FROM table",
//    executionPlan: "executionPlanexecutionPlan",
//    duration: 120,
//  });
//
//  const params = {
//    headers: {
//      'Content-Type': 'application/json',
//      'X-API-KEY': '3ae32a7910c145d4872f4e5e21c6cb64',
//    },
//  };
//
//  let res = http.post(url, payload, params);
//
//  check(res, {
//    'status is 200': (r) => r.status === 200,
//  });
//}
