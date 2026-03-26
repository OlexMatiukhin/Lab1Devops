import { useState, useEffect } from 'react';
import { getAllProducts, createProduct, updateProduct, addProductStock, deleteProduct } from '../api/productApi';
import { useToast } from '../components/ToastContext';
import Modal from '../components/Modal';
import ConfirmDialog from '../components/ConfirmDialog';

const emptyForm = { name: '', category: '', type: '', price: '', count: '' };

export default function ProductsPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [form, setForm] = useState(emptyForm);
  const [editId, setEditId] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [stockModal, setStockModal] = useState(null);
  const [stockCount, setStockCount] = useState('');
  const [filter, setFilter] = useState('all');
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try { setProducts(await getAllProducts()); } catch (e) { toast.error('Помилка завантаження товарів'); }
    setLoading(false);
  };

  useEffect(() => { load(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const dto = { ...form, price: parseFloat(form.price), count: parseInt(form.count) };
    try {
      if (editMode) {
        await updateProduct({ id: editId, ...dto });
        toast.success('Товар оновлено');
      } else {
        await createProduct(dto);
        toast.success('Товар створено');
      }
      setShowModal(false);
      setForm(emptyForm);
      load();
    } catch (e) { toast.error(e.message); }
  };

  const openEdit = (p) => {
    setForm({ name: p.name, category: p.category, type: p.type, price: p.price, count: p.count });
    setEditId(p.id);
    setEditMode(true);
    setShowModal(true);
  };

  const openCreate = () => { setForm(emptyForm); setEditMode(false); setShowModal(true); };

  const handleDelete = async () => {
    try { await deleteProduct(deleteId); toast.success('Товар видалено'); setDeleteId(null); load(); }
    catch (e) { toast.error(e.message); }
  };

  const handleAddStock = async (e) => {
    e.preventDefault();
    try {
      await addProductStock({ productId: stockModal.id, productsCount: parseInt(stockCount) });
      toast.success('Кількість поповнено');
      setStockModal(null);
      setStockCount('');
      load();
    } catch (e) { toast.error(e.message); }
  };

  const filtered = filter === 'all' ? products
    : filter === 'instock' ? products.filter(p => p.count > 0)
    : products.filter(p => p.count <= 0);

  if (loading) return <div className="spinner" />;

  return (
    <div className="fade-in">
      <div className="page-header">
        <h1>Товари</h1>
        <div className="actions">
          <select className="status-select" value={filter} onChange={e => setFilter(e.target.value)} style={{ marginRight: 8 }}>
            <option value="all">Усі</option>
            <option value="instock">В наявності</option>
            <option value="outofstock">Немає в наявності</option>
          </select>
          <button className="btn btn-primary" onClick={openCreate}>+ Додати товар</button>
        </div>
      </div>

      {products.length === 0 ? (
        <div className="empty-state"><div className="icon">📦</div><p>Товарів ще немає</p></div>
      ) : (
        <div className="table-wrapper">
          <table>
            <thead>
              <tr><th>ID</th><th>Назва</th><th>Категорія</th><th>Тип</th><th>Ціна</th><th>Кількість</th><th>Статус</th><th>Дії</th></tr>
            </thead>
            <tbody>
              {filtered.map(p => (
                <tr key={p.id}>
                  <td>{p.id}</td>
                  <td style={{ fontWeight: 500 }}>{p.name}</td>
                  <td>{p.category}</td>
                  <td>{p.type}</td>
                  <td>{p.price?.toFixed(2)} ₴</td>
                  <td>{p.count}</td>
                  <td>
                    <span className={`badge ${p.count > 0 ? 'badge-instock' : 'badge-outofstock'}`}>
                      {p.count > 0 ? 'В наявності' : 'Немає'}
                    </span>
                  </td>
                  <td>
                    <button className="btn-icon" title="Поповнити" onClick={() => { setStockModal(p); setStockCount(''); }}>📦</button>{' '}
                    <button className="btn-icon" title="Редагувати" onClick={() => openEdit(p)}>✏️</button>{' '}
                    <button className="btn-icon danger" title="Видалити" onClick={() => setDeleteId(p.id)}>🗑️</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <Modal title={editMode ? 'Редагувати товар' : 'Новий товар'} onClose={() => setShowModal(false)}>
          <form onSubmit={handleSubmit}>
            <div className="form-group"><label>Назва</label><input value={form.name} onChange={e => setForm({ ...form, name: e.target.value })} required /></div>
            <div className="form-group"><label>Категорія</label><input value={form.category} onChange={e => setForm({ ...form, category: e.target.value })} required /></div>
            <div className="form-group"><label>Тип</label><input value={form.type} onChange={e => setForm({ ...form, type: e.target.value })} required /></div>
            <div className="form-group"><label>Ціна</label><input type="number" step="0.01" min="0.01" value={form.price} onChange={e => setForm({ ...form, price: e.target.value })} required /></div>
            <div className="form-group"><label>Кількість</label><input type="number" min="0" value={form.count} onChange={e => setForm({ ...form, count: e.target.value })} required /></div>
            <div className="modal-actions">
              <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Скасувати</button>
              <button type="submit" className="btn btn-primary">{editMode ? 'Зберегти' : 'Створити'}</button>
            </div>
          </form>
        </Modal>
      )}

      {stockModal && (
        <Modal title={`Поповнити: ${stockModal.name}`} onClose={() => setStockModal(null)}>
          <form onSubmit={handleAddStock}>
            <div className="form-group">
              <label>Додати кількість</label>
              <input type="number" min="1" value={stockCount} onChange={e => setStockCount(e.target.value)} required autoFocus />
            </div>
            <div className="modal-actions">
              <button type="button" className="btn btn-secondary" onClick={() => setStockModal(null)}>Скасувати</button>
              <button type="submit" className="btn btn-primary">Поповнити</button>
            </div>
          </form>
        </Modal>
      )}

      {deleteId && (
        <ConfirmDialog message="Видалити цей товар?" onConfirm={handleDelete} onCancel={() => setDeleteId(null)} />
      )}
    </div>
  );
}
