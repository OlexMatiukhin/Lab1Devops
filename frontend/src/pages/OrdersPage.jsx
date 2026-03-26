import { useState, useEffect } from 'react';
import { getAllOrders, changeOrderStatus, deleteOrder } from '../api/orderApi';
import { useToast } from '../components/ToastContext';
import StatusBadge from '../components/StatusBadge';
import ConfirmDialog from '../components/ConfirmDialog';

const STATUSES = ['ACTIVE', 'FINISHED', 'RETURNED', 'CANCELLED'];

export default function OrdersPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [deleteId, setDeleteId] = useState(null);
  const [expandedId, setExpandedId] = useState(null);
  const [filterStatus, setFilterStatus] = useState('ALL');
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try { setOrders(await getAllOrders()); } catch (e) { toast.error('Помилка завантаження замовлень'); }
    setLoading(false);
  };

  useEffect(() => { load(); }, []);

  const handleStatusChange = async (orderId, newStatus) => {
    try {
      await changeOrderStatus({ orderId, orderStatus: newStatus });
      toast.success('Статус змінено');
      load();
    } catch (e) { toast.error(e.message); }
  };

  const handleDelete = async () => {
    try { await deleteOrder(deleteId); toast.success('Замовлення видалено'); setDeleteId(null); load(); }
    catch (e) { toast.error(e.message); }
  };

  const filtered = filterStatus === 'ALL' ? orders : orders.filter(o => o.status === filterStatus);

  if (loading) return <div className="spinner" />;

  return (
    <div className="fade-in">
      <div className="page-header">
        <h1>Замовлення</h1>
        <div className="actions">
          <select className="status-select" value={filterStatus} onChange={e => setFilterStatus(e.target.value)}>
            <option value="ALL">Усі статуси</option>
            {STATUSES.map(s => <option key={s} value={s}>{s}</option>)}
          </select>
        </div>
      </div>

      {orders.length === 0 ? (
        <div className="empty-state"><div className="icon">📋</div><p>Замовлень ще немає</p></div>
      ) : (
        <div className="table-wrapper">
          <table>
            <thead>
              <tr><th>ID</th><th>Клієнт</th><th>Статус</th><th>Позицій</th><th>Змінити статус</th><th>Дії</th></tr>
            </thead>
            <tbody>
              {filtered.map(o => (
                <>
                  <tr key={o.id} onClick={() => setExpandedId(expandedId === o.id ? null : o.id)} style={{ cursor: 'pointer' }}>
                    <td>#{o.id}</td>
                    <td>{o.clientName}</td>
                    <td><StatusBadge status={o.status} /></td>
                    <td>{o.orders?.length || 0}</td>
                    <td onClick={e => e.stopPropagation()}>
                      <select className="status-select" value={o.status} onChange={e => handleStatusChange(o.id, e.target.value)}>
                        {STATUSES.map(s => <option key={s} value={s}>{s}</option>)}
                      </select>
                    </td>
                    <td onClick={e => e.stopPropagation()}>
                      <button className="btn-icon danger" title="Видалити" onClick={() => setDeleteId(o.id)}>🗑️</button>
                    </td>
                  </tr>
                  {expandedId === o.id && o.orders && o.orders.length > 0 && (
                    <tr key={`${o.id}-detail`}>
                      <td colSpan={6}>
                        <div className="detail-panel">
                          <h4>Позиції замовлення</h4>
                          <table>
                            <thead><tr><th>Товар</th><th>Кількість</th><th>Ціна</th></tr></thead>
                            <tbody>
                              {o.orders.map((item, i) => (
                                <tr key={i}>
                                  <td>{item.productName}</td>
                                  <td>{item.count}</td>
                                  <td>{item.totalPrice?.toFixed(2)} ₴</td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      </td>
                    </tr>
                  )}
                </>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {deleteId && (
        <ConfirmDialog message={`Видалити замовлення #${deleteId}?`} onConfirm={handleDelete} onCancel={() => setDeleteId(null)} />
      )}
    </div>
  );
}
