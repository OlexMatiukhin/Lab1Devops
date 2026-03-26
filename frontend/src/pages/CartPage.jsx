import { useState, useEffect } from 'react';
import { getAllCart, addToCart, buyFromCart } from '../api/cartApi';
import { getAllClients } from '../api/clientApi';
import { getAllProducts } from '../api/productApi';
import { useToast } from '../components/ToastContext';
import Modal from '../components/Modal';

export default function CartPage() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showBuyModal, setShowBuyModal] = useState(false);
  const [clients, setClients] = useState([]);
  const [products, setProducts] = useState([]);
  const [addForm, setAddForm] = useState({ productId: '', clientId: '', quantity: '' });
  const [buyForm, setBuyForm] = useState({ clientId: '', adress: '' });
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try { setCartItems(await getAllCart()); } catch (e) { toast.error('Помилка завантаження кошика'); }
    setLoading(false);
  };

  useEffect(() => { load(); }, []);

  const openAddModal = async () => {
    try {
      const [c, p] = await Promise.all([getAllClients(), getAllProducts()]);
      setClients(c);
      setProducts(p.filter(pr => pr.count > 0));
      setAddForm({ productId: '', clientId: '', quantity: '' });
      setShowAddModal(true);
    } catch (e) { toast.error('Помилка завантаження даних'); }
  };

  const openBuyModal = async () => {
    try {
      const c = await getAllClients();
      setClients(c);
      setBuyForm({ clientId: '', adress: '' });
      setShowBuyModal(true);
    } catch (e) { toast.error('Помилка завантаження клієнтів'); }
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    try {
      await addToCart({
        productId: parseInt(addForm.productId),
        clientId: parseInt(addForm.clientId),
        quantity: parseInt(addForm.quantity),
      });
      toast.success('Товар додано до кошика');
      setShowAddModal(false);
      load();
    } catch (e) { toast.error(e.message); }
  };

  const handleBuy = async (e) => {
    e.preventDefault();
    try {
      await buyFromCart({ clientId: parseInt(buyForm.clientId), adress: buyForm.adress });
      toast.success('Замовлення оформлено!');
      setShowBuyModal(false);
      load();
    } catch (e) { toast.error(e.message); }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="fade-in">
      <div className="page-header">
        <h1>Кошик</h1>
        <div className="actions">
          <button className="btn btn-secondary" onClick={openBuyModal}>🛍️ Оформити замовлення</button>
          <button className="btn btn-primary" onClick={openAddModal}>+ Додати до кошика</button>
        </div>
      </div>

      {cartItems.length === 0 ? (
        <div className="empty-state"><div className="icon">🛒</div><p>Кошик порожній</p></div>
      ) : (
        <div className="table-wrapper">
          <table>
            <thead>
              <tr><th>ID</th><th>Товар</th><th>Клієнт</th><th>Кількість</th><th>Сума</th></tr>
            </thead>
            <tbody>
              {cartItems.map(item => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td style={{ fontWeight: 500 }}>{item.productName}</td>
                  <td>{item.clientName}</td>
                  <td>{item.count}</td>
                  <td>{item.totalPrice?.toFixed(2)} ₴</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showAddModal && (
        <Modal title="Додати товар до кошика" onClose={() => setShowAddModal(false)}>
          <form onSubmit={handleAdd}>
            <div className="form-group">
              <label>Клієнт</label>
              <select value={addForm.clientId} onChange={e => setAddForm({ ...addForm, clientId: e.target.value })} required>
                <option value="">Оберіть клієнта</option>
                {clients.map(c => <option key={c.id} value={c.id}>{c.firstName} {c.lastName} ({c.email || c.Email})</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Товар</label>
              <select value={addForm.productId} onChange={e => setAddForm({ ...addForm, productId: e.target.value })} required>
                <option value="">Оберіть товар</option>
                {products.map(p => <option key={p.id} value={p.id}>{p.name} (залишок: {p.count})</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Кількість</label>
              <input type="number" min="1" value={addForm.quantity} onChange={e => setAddForm({ ...addForm, quantity: e.target.value })} required />
            </div>
            <div className="modal-actions">
              <button type="button" className="btn btn-secondary" onClick={() => setShowAddModal(false)}>Скасувати</button>
              <button type="submit" className="btn btn-primary">Додати</button>
            </div>
          </form>
        </Modal>
      )}

      {showBuyModal && (
        <Modal title="Оформити замовлення" onClose={() => setShowBuyModal(false)}>
          <form onSubmit={handleBuy}>
            <div className="form-group">
              <label>Клієнт</label>
              <select value={buyForm.clientId} onChange={e => setBuyForm({ ...buyForm, clientId: e.target.value })} required>
                <option value="">Оберіть клієнта</option>
                {clients.map(c => <option key={c.id} value={c.id}>{c.firstName} {c.lastName}</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Адреса доставки</label>
              <input value={buyForm.adress} onChange={e => setBuyForm({ ...buyForm, adress: e.target.value })} placeholder="Вулиця, будинок, квартира" required />
            </div>
            <div className="modal-actions">
              <button type="button" className="btn btn-secondary" onClick={() => setShowBuyModal(false)}>Скасувати</button>
              <button type="submit" className="btn btn-primary">Оформити</button>
            </div>
          </form>
        </Modal>
      )}
    </div>
  );
}
