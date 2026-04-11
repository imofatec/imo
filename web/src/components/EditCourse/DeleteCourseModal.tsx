import Button from '@/components/ui/Button'

type Props = {
  isOpen: boolean
  courseName?: string
  onClose: () => void
  onConfirm: () => void
}

export default function DeleteCourseModal({ isOpen, courseName, onClose, onConfirm }: Props) {
  if (!isOpen) return null

  return (
    <div
      onClick={onClose}
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4"
    >
      <div
        onClick={(event) => event.stopPropagation()}
        className="w-full max-w-xl rounded-2xl border border-white/10 bg-[#14082f] p-6 shadow-2xl"
      >
        <div className="flex items-start justify-between gap-4">
          <div>
            <span className="inline-block rounded-full bg-red-500/15 px-3 py-1 text-xs font-medium text-red-300">
              Zona de perigo
            </span>
            <h2 className="mt-3 text-2xl font-bold text-white">Excluir curso</h2>
          </div>

          <button
            type="button"
            onClick={onClose}
            className="rounded-lg border border-white/10 bg-white/5 px-3 py-2 text-sm text-white hover:bg-white/10"
          >
            Fechar
          </button>
        </div>

        <div className="mt-5 space-y-4">
          <p className="text-sm leading-7 text-white">
            Tem certeza que deseja excluir este curso
            {courseName ? `, "${courseName}"` : ''}?
          </p>

          <div className="grid gap-3 sm:grid-cols-2">
            <Button
              type="button"
              onClick={onClose}
              className="w-full rounded-xl border border-white/20 bg-white/5 px-4 py-3 text-sm font-medium text-white! hover:bg-white/10"
            >
              Cancelar
            </Button>

            <Button
              type="button"
              onClick={onConfirm}
              className="w-full rounded-xl border border-red-400/40 bg-red-500/10 px-4 py-3 text-sm font-medium text-white! hover:bg-red-500/15"
            >
              Confirmar exclusão
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}
