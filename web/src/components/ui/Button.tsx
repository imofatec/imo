import type { ButtonHTMLAttributes } from 'react'

const buttonVariants = {
  default:
    'h-10 rounded-md text-sm font-medium whitespace-nowrap ring-offset-white focus-visible:ring-2 focus-visible:ring-slate-950 focus-visible:ring-offset-2 dark:text-slate-900 dark:ring-offset-slate-950 dark:focus-visible:ring-slate-300',
  cyanOutline:
    'mt-5 w-full rounded-xl border border-cyan bg-cyan/10 px-4 py-3 text-center text-sm font-medium hover:bg-cyan/20 focus-visible:ring-2 focus-visible:ring-cyan/40',
} as const

type ButtonVariant = keyof typeof buttonVariants

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: ButtonVariant
}

export default function Button({
  children,
  className = '',
  variant = 'default',
  ...props
}: ButtonProps) {
  return (
    <button
      className={`inline-flex items-center justify-center transition-transform duration-200 focus-visible:outline-none disabled:pointer-events-none disabled:opacity-50 ${buttonVariants[variant]} ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}
