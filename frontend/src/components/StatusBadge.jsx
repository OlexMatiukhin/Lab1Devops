const map = {
  ACTIVE: 'badge-active',
  FINISHED: 'badge-finished',
  RETURNED: 'badge-returned',
  CANCELLED: 'badge-cancelled',
};

const labels = {
  ACTIVE: 'Активне',
  FINISHED: 'Завершене',
  RETURNED: 'Повернене',
  CANCELLED: 'Скасоване',
};

export default function StatusBadge({ status }) {
  return (
    <span className={`badge ${map[status] || ''}`}>
      {labels[status] || status}
    </span>
  );
}
