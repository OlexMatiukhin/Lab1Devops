import { useState, useEffect } from 'react';
import { getAllClients } from '../api/clientApi';
import { getAllProducts } from '../api/productApi';
import { getAllOrders } from '../api/orderApi';
import { getAllCart } from '../api/cartApi';
import StatusBadge from '../components/StatusBadge';

export default function Dashboard() {
  const [stats, setStats] = useState({ clients: 0, products: 0, orders: 0, cart: 0 });
  const [recentOrders, setRecentOrders] = useState([]);
  const [outOfStock, setOutOfStock] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function load() {
      try {
        const [clients, products, orders, cart] = await Promise.allSettled([
          getAllClients(),
          getAllProducts(),
          getAllOrders(),
          getAllCart(),
        ]);
        const c = clients.status === 'fulfilled' ? clients.value : [];
        const p = products.status === 'fulfilled' ? products.value : [];
        const o = orders.status === 'fulfilled' ? orders.value : [];
        const ca = cart.status === 'fulfilled' ? cart.value : [];
        setStats({ clients: c.length, products: p.length, orders: o.length, cart: ca.length });
        setRecentOrders(o.slice(-5).reverse());
        setOutOfStock(p.filter(pr => pr.count <= 0));
      } catch (e) { console.error(e); }
      setLoading(false);
    }
    load();
  }, []);

  if (loading) return <div className="spinner" />;

  return (
    <div className="fade-in">
      <div className="page-header"><h1>Головна панель</h1></div>

      <div className="metrics">
        <div className="metric-card">
          <div className="metric-icon">👥</div>
          <div className="metric-value">{stats.clients}</div>
          <div className="metric-label">Клієнтів</div>
        </div>
        <div className="metric-card">
          <div className="metric-icon">📦</div>
          <div className="metric-value">{stats.products}</div>
          <div className="metric-label">Товарів</div>
        </div>
        <div className="metric-card">
          <div className="metric-icon">📋</div>
          <div className="metric-value">{stats.orders}</div>
          <div className="metric-label">Замовлень</div>
        </div>
        <div className="metric-card">
          <div className="metric-icon">🛒</div>
          <div className="metric-value">{stats.cart}</div>
          <div className="metric-label">У кошиках</div>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 20 }}>
        <div className="card">
          <h3 style={{ marginBottom: 16 }}>Останні замовлення</h3>
          {recentOrders.length === 0 ? (
            <p style={{ color: 'var(--text-secondary)', fontSize: 14 }}>Немає замовлень</p>
          ) : (
            <table>
              <thead><tr><th>ID</th><th>Клієнт</th><th>Статус</th></tr></thead>
              <tbody>
                {recentOrders.map(o => (
                  <tr key={o.id}>
                    <td>#{o.id}</td>
                    <td>{o.clientName}</td>
                    <td><StatusBadge status={o.status} /></td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>

        <div className="card">
          <h3 style={{ marginBottom: 16 }}>Немає в наявності</h3>
          {outOfStock.length === 0 ? (
            <p style={{ color: 'var(--text-secondary)', fontSize: 14 }}>Все в наявності ✅</p>
          ) : (
            <table>
              <thead><tr><th>Назва</th><th>Категорія</th></tr></thead>
              <tbody>
                {outOfStock.map(p => (
                  <tr key={p.id}>
                    <td>{p.name}</td>
                    <td>{p.category}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}
