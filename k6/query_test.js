import http from 'k6/http';
import { sleep, check } from 'k6';


// k6 run --out influxdb=http://localhost:8086/k6 --threshold-warnings query_test.js



export const options = {
  scenarios: {
    constant_rate_test: {
      executor: 'constant-arrival-rate',
      rate: 500,                // 초당 500개의 요청
      timeUnit: '1s',           // 요청 단위 시간 (초당)
      duration: '10s',          // 테스트 지속 시간
      preAllocatedVUs: 200,     // 사전 할당 VU 수
      maxVUs: 500,              // 최대 VU 수
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],       // 실패율 1% 미만
    http_req_duration: ['p(95)<3000'],    // 95% 요청은 3초 이내 응답
  },
};

export default function () {
  const url = 'http://localhost:8080/api/query-logs';
  const payload = JSON.stringify({
    methodName: "methodname",
    sqlQuery: "SELECT * FROM table",
    executionPlan: "executionPlanexecutionPlan",
    duration: 120,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'X-API-KEY': '3ae32a7910c145d4872f4e5e21c6cb64',
    },
  };

  let res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}
