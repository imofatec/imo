import type { InputHTMLAttributes } from 'react'

type FormInputProps = InputHTMLAttributes<HTMLInputElement> & {
  label?: string
  error?: string
}

export default function FormInput({ label, className = '', id,error, ...props }: FormInputProps) {
  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      {label && <label htmlFor={id}>{label}</label>}
      <input
        id={id}
        className={`${error ? 'border-red-500 focus:border-red-500' : 'border-custom-border-gray focus:border-white'} flex h-10 w-full rounded-md border px-3 py-2 text-sm text-white file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-slate-500 focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 bg-custom-blue dark:placeholder:text-slate-400 ${className}`}
        {...props}
      />
      {error && <p className="text-red-500 text-sm mt-1">{error}</p>}

    </div>
  )
}
