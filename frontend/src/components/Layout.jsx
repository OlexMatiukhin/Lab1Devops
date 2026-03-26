import { NavLink } from 'react-router-dom';
import './Layout.css';

const navItems = [
  { to: '/',         icon: '📊', label: 'Головна' },
  { to: '/clients',  icon: '👥', label: 'Клієнти' },
  { to: '/products', icon: '📦', label: 'Товари' },
  { to: '/orders',   icon: '📋', label: 'Замовлення' },
  { to: '/cart',     icon: '🛒', label: 'Кошик' },
];

export default function Layout({ children }) {
  return (
    <div className="layout">
      <aside className="sidebar">
        <div className="sidebar-logo">
          <span className="logo-icon">🛒</span>
          <span className="logo-text">ShopAdmin</span>
        </div>
        <nav className="sidebar-nav">
          {navItems.map(item => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === '/'}
              className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
            >
              <span className="nav-icon">{item.icon}</span>
              <span className="nav-label">{item.label}</span>
            </NavLink>
          ))}
        </nav>
        <div className="sidebar-footer">
          <div className="sidebar-footer-text">Microservices Shop</div>
          <div className="sidebar-footer-version">v1.0.0</div>
        </div>
      </aside>
      <main className="main-content">
        {children}
      </main>
    </div>
  );
}
