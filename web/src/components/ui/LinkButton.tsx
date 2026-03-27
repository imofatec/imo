import type { AnchorHTMLAttributes } from 'react'
import { Link } from 'react-router-dom'

type LinkButtonProps = AnchorHTMLAttributes<HTMLAnchorElement> & {
  to: string
}

export default function LinkButton({ children, className = '', to, ...props }: LinkButtonProps) {
  return (
    <Link
      to={to}
      className={`inline-flex h-10 items-center justify-center rounded-md text-sm font-medium whitespace-nowrap ring-offset-white transition-transform duration-200 focus-visible:ring-2 focus-visible:ring-slate-950 focus-visible:ring-offset-2 focus-visible:outline-none dark:text-slate-900 dark:ring-offset-slate-950 dark:focus-visible:ring-slate-300 ${className}`}
      {...props}
    >
      {children}
    </Link>
  )
}
