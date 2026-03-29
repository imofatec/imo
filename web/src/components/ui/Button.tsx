import type { ButtonHTMLAttributes } from 'react'

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {}

export default function Button({ children, className = '', ...props }: ButtonProps) {
  return (
    <button
      className={`inline-flex h-10 items-center justify-center rounded-md text-sm font-medium whitespace-nowrap ring-offset-white transition-transform duration-200 focus-visible:ring-2 focus-visible:ring-slate-950 focus-visible:ring-offset-2 focus-visible:outline-none disabled:pointer-events-none disabled:opacity-50 dark:text-slate-900 dark:ring-offset-slate-950 dark:focus-visible:ring-slate-300 ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}
