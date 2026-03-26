const BASE = '/api/v1/productsInCart';

export async function getAllCart() {
  const res = await fetch(BASE);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function getCartByClientId(clientId) {
  const res = await fetch(`${BASE}/${clientId}`);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function addToCart(dto) {
  const res = await fetch(`${BASE}/add_product`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}

export async function buyFromCart(dto) {
  const res = await fetch(`${BASE}/buy_from_cart`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}
