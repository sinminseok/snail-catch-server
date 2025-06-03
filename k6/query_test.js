import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 20,         // 20명의 가상 유저가
  duration: '10s', // 10초 동안 실행
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

  sleep(1);  // 1초에 1번 요청 (VU 당)
}
