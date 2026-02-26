import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/ko';

export const formatTime = (date) => {
  const now = dayjs();
  const targetDate = dayjs(date);

  // 24시간 이내라면 '방금 전', '3시간 전' 등으로 표시
  if (now.diff(targetDate, 'hour') < 24) {
    return targetDate.fromNow();
  }
  
  // 올해 게시물이라면 'M월 D일'
  if (now.isSame(targetDate, 'year')) {
    return targetDate.format('M월 D일');
  }
  
  // 그 외(작년 등)는 'YYYY.MM.DD'
  return targetDate.format('YYYY.MM.DD');
};