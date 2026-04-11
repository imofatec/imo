import Button from '@/components/ui/Button'
import type { User } from '@/types/user'

type Props = {
  user: User | null
}

export default function AccountStatusSection({ user }: Props) {
  const isConfirmed = Boolean(user?.isConfirmed)
  const email = user?.email || 'E-mail'

  return (
    <div className="space-y-4">
      <div className="flex flex-wrap items-center gap-3">
        <span className="text-lg text-white/70">Estado da conta:</span>
        <span
          className={`rounded-full px-3 py-1 text-lg font-semibold ${
            isConfirmed
              ? 'border border-emerald-400/40 bg-emerald-400/15 text-emerald-200'
              : 'border border-amber-400/40 bg-amber-400/15 text-amber-200'
          }`}
        >
          {isConfirmed ? 'Conta ativa' : 'Verifique sua conta!'}
        </span>
      </div>

      <div className="p-4">
        <p className="text-sm text-white/80">E-mail de verificação: {email}</p>
      </div>

      <div className="flex justify-end">
        <Button
          type="button"
          variant="cyanOutline"
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
          disabled={isConfirmed}
        >
          Reenviar e-mail de confirmação
        </Button>
      </div>
    </div>
  )
}
