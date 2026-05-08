import type { AnchorHTMLAttributes } from 'react'
import { Link } from 'react-router-dom'

const linkButtonVariants = {
  default:
    'h-10 rounded-md text-sm font-medium whitespace-nowrap ring-offset-white focus-visible:ring-2 focus-visible:ring-slate-950 focus-visible:ring-offset-2 dark:text-slate-900 dark:ring-offset-slate-950 dark:focus-visible:ring-slate-300',
  cyanOutline:
    'mt-5 w-full rounded-xl border border-cyan bg-cyan/10 px-4 py-3 text-center text-sm font-medium hover:bg-cyan/20 focus-visible:ring-2 focus-visible:ring-cyan/40',
} as const

type LinkButtonVariant = keyof typeof linkButtonVariants

type LinkButtonProps = AnchorHTMLAttributes<HTMLAnchorElement> & {
  to: string
  variant?: LinkButtonVariant
}

export default function LinkButton({
  children,
  className = '',
  to,
  variant = 'default',
  ...props
}: LinkButtonProps) {
  return (
    <Link
      to={to}
      className={`inline-flex items-center justify-center transition-transform duration-200 focus-visible:outline-none ${linkButtonVariants[variant]} ${className}`}
      {...props}
    >
      {children}
    </Link>
  )
}
