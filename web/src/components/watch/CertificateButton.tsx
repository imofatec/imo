type Props = {
  disabled?: boolean
}

export default function CertificateButton({ disabled = true }: Props) {
  return (
    <button
      type="button"
      disabled={disabled}
      className={`mt-5 w-full rounded-xl border px-4 py-3 text-sm font-medium transition ${
        disabled
          ? 'border-cyan/30 bg-cyan/10 text-cyan/60 cursor-not-allowed'
          : 'border-cyan bg-cyan/20 text-cyan hover:bg-cyan/30'
      }`}
    >
      Certificado
    </button>
  )
}
