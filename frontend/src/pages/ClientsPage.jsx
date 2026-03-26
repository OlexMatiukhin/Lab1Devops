import { useState, useEffect } from 'react';
import { getAllClients, createClient, updateClient, deleteClient } from '../api/clientApi';
import { useToast } from '../components/ToastContext';
import Modal from '../components/Modal';
import ConfirmDialog from '../components/ConfirmDialog';
import StatusBadge from '../components/StatusBadge';

const emptyForm = { firstName: '', lastName: '', email: '', phone: '', birthDate: '' };

export default function ClientsPage() {
  const [clients, setClients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [form, setForm] = useState(emptyForm);
  const [editId, setEditId] = useState(null);
  const [deleteEmail, setDeleteEmail] = useState(null);
  const [expandedId, setExpandedId] = useState(null);
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try { setClients(await getAllClients()); } catch (e) { toast.error('Помилка завантаження клієнтів'); }
    setLoading(false);
  };

  useEffect(() => { load(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await updateClient({ id: editId, firstName: form.firstName, lastName: form.lastName, birthDate: form.birthDate });
        toast.success('Клієнта оновлено');
      } else {
        await createClient(form);
        toast.success('Клієнта створено');
      }
      setShowModal(false);
      setForm(emptyForm);
      load();
    } catch (e) { toast.error(e.message); }
  };

  const openEdit = (c) => {
    setForm({ firstName: c.firstName, lastName: c.lastName, email: c.email || c.Email, phone: c.phone || c.Phone, birthDate: c.birthDate || '' });
    setEditId(c.id);
    setEditMode(true);
    setShowModal(true);
  };

  const openCreate = () => {
    setForm(emptyForm);
    setEditMode(false);
    setShowModal(true);
  };

  const handleDelete = async () => {
    try {
      await deleteClient(deleteEmail);
      toast.success('Клієнта видалено');
      setDeleteEmail(null);
      load();
    } catch (e) { toast.error(e.message); }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="fade-in">
      <div className="page-header">
        <h1>Клієнти</h1>
        <div className="actions">
          <button className="btn btn-primary" onClick={openCreate}>+ Додати клієнта</button>
        </div>
      </div>

      {clients.length === 0 ? (
        <div className="empty-state">
          <div className="icon">👥</div>
          <p>Клієнтів ще немає</p>
        </div>
      ) : (
        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>ID</th><th>Ім'я</th><th>Прізвище</th><th>Email</th><th>Телефон</th><th>Вік</th><th>Дії</th>
              </tr>
            </thead>
            <tbody>
              {clients.map(c => (
                <>
                  <tr key={c.id} onClick={() => setExpandedId(expandedId === c.id ? null : c.id)} style={{ cursor: 'pointer' }}>
                    <td>{c.id}</td>
                    <td>{c.firstName}</td>
                    <td>{c.lastName}</td>
                    <td>{c.email || c.Email}</td>
                    <td>{c.phone || c.Phone}</td>
                    <td>{c.age}</td>
                    <td onClick={e => e.stopPropagation()}>
                      <button className="btn-icon" title="Редагувати" onClick={() => openEdit(c)}>✏️</button>{' '}
                      <button className="btn-icon danger" title="Видалити" onClick={() => setDeleteEmail(c.email || c.Email)}>🗑️</button>
                    </td>
                  </tr>
                  {expandedId === c.id && (
                    <tr key={`${c.id}-detail`}>
                      <td colSpan={7}>
                        <div className="detail-panel">
                          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 16 }}>
                            <div>
                              <h4>🛒 Кошик</h4>
                              {(!c.products || c.products.length === 0)
                                ? <p style={{ color: 'var(--text-muted)', fontSize: 13 }}>Порожній</p>
                                : <table><thead><tr><th>Товар</th><th>Кількість</th><th>Ціна</th></tr></thead>
                                    <tbody>{c.products.map((p, i) => <tr key={i}><td>{p.productName}</td><td>{p.count}</td><td>{p.totalPrice?.toFixed(2)} ₴</td></tr>)}</tbody></table>
                              }
                            </div>
                            <div>
                              <h4>📋 Замовлення</h4>
                              {(!c.orders || c.orders.length === 0)
                                ? <p style={{ color: 'var(--text-muted)', fontSize: 13 }}>Немає</p>
                                : <table><thead><tr><th>ID</th><th>Статус</th><th>Позицій</th></tr></thead>
                                    <tbody>{c.orders.map(o => <tr key={o.id}><td>#{o.id}</td><td><StatusBadge status={o.status} /></td><td>{o.orders?.length || 0}</td></tr>)}</tbody></table>
                              }
                            </div>
                          </div>
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

      {showModal && (
        <Modal title={editMode ? 'Редагувати клієнта' : 'Новий клієнт'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Ім'я</label>
              <input value={form.firstName} onChange={e => setForm({ ...form, firstName: e.target.value })} required />
            </div>
            <div className="form-group">
              <label>Прізвище</label>
              <input value={form.lastName} onChange={e => setForm({ ...form, lastName: e.target.value })} required />
            </div>
            {!editMode && (
              <>
                <div className="form-group">
                  <label>Email</label>
                  <input type="email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} required />
                </div>
                <div className="form-group">
                  <label>Телефон (+380...)</label>
                  <input value={form.phone} onChange={e => setForm({ ...form, phone: e.target.value })} placeholder="+380XXXXXXXXX" required />
                </div>
              </>
            )}
            <div className="form-group">
              <label>Дата народження</label>
              <input type="date" value={form.birthDate} onChange={e => setForm({ ...form, birthDate: e.target.value })} required />
            </div>
            <div className="modal-actions">
              <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Скасувати</button>
              <button type="submit" className="btn btn-primary">{editMode ? 'Зберегти' : 'Створити'}</button>
            </div>
          </form>
        </Modal>
      )}

      {deleteEmail && (
        <ConfirmDialog
          message={`Видалити клієнта з email ${deleteEmail}?`}
          onConfirm={handleDelete}
          onCancel={() => setDeleteEmail(null)}
        />
      )}
    </div>
  );
}
