const BASE = '/api/v1/clients';

export async function getAllClients() {
  const res = await fetch(BASE);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function getClientByEmail(email) {
  const res = await fetch(`${BASE}/email/${encodeURIComponent(email)}`);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function getClientById(id) {
  const res = await fetch(`${BASE}/id/${id}`);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function createClient(dto) {
  const res = await fetch(BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}

export async function updateClient(dto) {
  const res = await fetch(BASE, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dto),
  });
  if (!res.ok) throw new Error(await res.text());
}

export async function deleteClient(email) {
  const res = await fetch(`${BASE}/${encodeURIComponent(email)}`, { method: 'DELETE' });
  if (!res.ok) throw new Error(await res.text());
}
