import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50,
  duration: '30s',
};

const BASE_URL = 'http://localhost:8080';
const apiKey = 'c4db0348054449be9ee75da4e8d98729';

function randomDateISOString() {
  // 현재시간 기준으로 최대 10분 전~ 현재 사이 랜덤 날짜 생성 (예시)
  const now = new Date();
  const past = new Date(now.getTime() - 10 * 60 * 1000); // 10분 전
  const randomTimestamp = past.getTime() + Math.random() * (now.getTime() - past.getTime());
  const randomDate = new Date(randomTimestamp);
  return randomDate.toISOString(); // ISO8601, UTC 기준 시간 포함 (ex: 2025-06-11T02:50:04.282Z)
}

export default function () {
  const cursorCreatedAt = randomDateISOString();
  const size = 10;

  const url = `${BASE_URL}/api/query-logs/cursor?cursorCreatedAt=${encodeURIComponent(cursorCreatedAt)}&size=${size}`;

  const res = http.get(url, {
    headers: {
      'x-api-key': apiKey,
    },
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });


}


