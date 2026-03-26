const BASE = '/api/v1/orders';

export async function getAllOrders() {
  const res = await fetch(BASE);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function getOrdersByClientId(clientId) {
  const res = await fetch(`${BASE}/${clientId}`);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function saveOrder(dto) {
  const res = await fetch(`${BASE}/save_order`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}

export async function changeOrderStatus(dto) {
  const res = await fetch(`${BASE}/change_status`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}

export async function deleteOrder(orderId) {
  const res = await fetch(`${BASE}/${orderId}`, { method: 'DELETE' });
  if (!res.ok) throw new Error(await res.text());
}
