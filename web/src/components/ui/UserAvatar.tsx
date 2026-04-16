import { CircleUserRound } from 'lucide-react'
import { resolveProfileImageSrc } from '@/lib/resolveProfileImageSrc'

type UserAvatarProps = {
  imageSrc?: string | null
  name?: string | null
  sizeClassName?: string
  fallback?: 'initials' | 'icon'
  className?: string
  iconClassName?: string
}

function getInitials(value: string) {
  const parts = value.trim().split(' ').filter(Boolean)

  return parts
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('')
}

export default function UserAvatar({
  imageSrc,
  name,
  sizeClassName = 'h-9 w-9',
  fallback = 'initials',
  className = '',
  iconClassName = 'text-cyan',
}: UserAvatarProps) {
  const resolvedImageSrc = resolveProfileImageSrc(imageSrc ?? null)

  if (resolvedImageSrc) {
    return (
      <img
        src={resolvedImageSrc}
        alt={name ? `Foto de perfil de ${name}` : 'Foto de perfil do usuário'}
        className={`${sizeClassName} rounded-full object-cover ${className}`}
      />
    )
  }

  if (fallback === 'icon') {
    return (
      <div className={`flex items-center justify-center ${sizeClassName} ${className}`}>
        <CircleUserRound size={32} className={iconClassName} />
      </div>
    )
  }

  return (
    <div
      className={`flex items-center justify-center rounded-full border border-white/40 bg-white/5 text-xs font-semibold text-white/85 ${sizeClassName} ${className}`}
    >
      {getInitials(name ?? 'Usuário')}
    </div>
  )
}
