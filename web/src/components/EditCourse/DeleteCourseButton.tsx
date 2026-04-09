import Button from '@/components/ui/Button'

type Props = {
  onDelete: () => void
}

export default function DeleteCourseButton({ onDelete }: Props) {
  return (
    <Button
      type="button"
      onClick={onDelete}
      className="w-full rounded-xl border border-red-400/40 bg-red-500/10 py-3 text-base font-semibold text-white/60! hover:bg-red-500/15 hover:text-white!"
    >
      Excluir curso
    </Button>
  )
}
