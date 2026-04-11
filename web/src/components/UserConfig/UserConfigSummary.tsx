import type { User } from '@/types/user'
import Button from '@/components/ui/Button'
import { Camera } from 'lucide-react'

type Props = {
  user: User | null
}

export default function UserConfigSummary({ user }: Props) {
  const userName = user?.name?.trim() || 'Usuário'
  const userEmail = user?.email || 'E-mail'

  return (
    <div className="flex flex-col gap-4 rounded-3xl border border-white/10 bg-[#14082f] p-6 md:flex-row md:items-center md:justify-between">
      <div className="flex items-center gap-4">
        <div className="group relative">
          <div className="border-cyan/40 bg-cyan/10 text-cyan flex h-16 w-16 items-center justify-center overflow-hidden rounded-full border text-xl font-semibold group-hover:scale-75 group-hover:opacity-0">
            {userName.charAt(0).toUpperCase()}
          </div>

          <Button
            type="button"
            className="text-cyan! absolute inset-3 flex items-center justify-center rounded-full bg-black/0 opacity-0 transition-all duration-200 group-hover:opacity-100"
          >
            <Camera size={32} />
          </Button>
        </div>

        <div>
          <p className="text-lg font-semibold text-white">{userName}</p>
          <p className="text-sm text-white/60">{userEmail}</p>
        </div>
      </div>
    </div>
  )
}
