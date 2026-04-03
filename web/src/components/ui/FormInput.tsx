import type { InputHTMLAttributes } from 'react'

type FormInputProps = InputHTMLAttributes<HTMLInputElement> & {
  label?: string
  error?: string
}

export default function FormInput({ label, className = '', id, error, ...props }: FormInputProps) {
  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      {label && <label htmlFor={id}>{label}</label>}
      <input
        id={id}
        className={`${error ? 'border-red-500' : 'border-white/10'} flex h-10 w-full rounded-md border px-3 py-2 text-sm text-white placeholder:text-white/40 focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${className}`}
        {...props}
      />
      <p className={`mt-1 min-h-5 text-sm leading-5 ${error ? 'text-red-500' : 'text-transparent'}`}>
        {error ?? ' '}
      </p>
    </div>
  )
}
