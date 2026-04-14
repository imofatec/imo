import { Eye, EyeOff } from 'lucide-react'

type PasswordVisibilityButtonProps = {
  visible: boolean
  disabled?: boolean
  onToggle: () => void
}

export default function PasswordVisibilityButton({
  visible,
  disabled = false,
  onToggle,
}: PasswordVisibilityButtonProps) {
  return (
    <button
      type="button"
      aria-label={visible ? 'Ocultar senha' : 'Mostrar senha'}
      aria-pressed={visible}
      onClick={onToggle}
      className="absolute inset-y-0 right-0 flex w-11 items-center justify-center text-white/60 transition hover:text-white focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50"
      disabled={disabled}
    >
      {visible ? <EyeOff size={18} /> : <Eye size={18} />}
    </button>
  )
}
