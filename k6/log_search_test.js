import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50, // 동시에 실행할 가상 유저 수 (Virtual Users)
  duration: '30s', // 테스트 시간
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% 요청은 500ms 이하로 완료되어야 함
    http_req_failed: ['rate<0.01'], // 실패율은 1% 미만이어야 함
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  const page = Math.floor(Math.random() * 10); // 0~9 랜덤 페이지
  const size = 15;
  const apiKey = 'test-api-key'; // 필요 시 유효한 API 키로 대체하세요

  const res = http.get(`${BASE_URL}/api/query-logs?page=${page}&size=${size}`, {
    headers: {
      'x-api-key': apiKey,
    },
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1); // 다음 요청 전 1초 대기
}