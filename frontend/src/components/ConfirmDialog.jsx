import Modal from './Modal';

export default function ConfirmDialog({ message, onConfirm, onCancel }) {
  return (
    <Modal title="Підтвердження" onClose={onCancel}>
      <p className="confirm-text">{message}</p>
      <div className="modal-actions">
        <button className="btn btn-secondary" onClick={onCancel}>Скасувати</button>
        <button className="btn btn-danger" onClick={onConfirm}>Видалити</button>
      </div>
    </Modal>
  );
}
