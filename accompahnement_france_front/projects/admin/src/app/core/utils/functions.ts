export function timeAgoFr(dateString: string): string {
  const now: Date = new Date();
  const past: Date = new Date(dateString);

  const diffInSeconds: number = Math.floor(
    (now.getTime() - past.getTime()) / 1000
  );

  if (diffInSeconds < 60) {
    return 'il y a quelques secondes';
  }

  const minutes: number = Math.floor(diffInSeconds / 60);
  if (minutes < 60) {
    return `il y a ${minutes} minute${minutes > 1 ? 's' : ''}`;
  }

  const hours: number = Math.floor(minutes / 60);
  if (hours < 24) {
    return `il y a ${hours} heure${hours > 1 ? 's' : ''}`;
  }

  const days: number = Math.floor(hours / 24);
  if (days < 7) {
    return `il y a ${days} jour${days > 1 ? 's' : ''}`;
  }

  const weeks: number = Math.floor(days / 7);
  if (weeks < 4) {
    return `il y a ${weeks} semaine${weeks > 1 ? 's' : ''}`;
  }

  const months: number = Math.floor(days / 30);
  if (months < 12) {
    return `il y a ${months} mois`;
  }

  const years: number = Math.floor(days / 365);
  return `il y a ${years} an${years > 1 ? 's' : ''}`;
}

