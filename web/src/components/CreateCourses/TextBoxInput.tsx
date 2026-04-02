import type { TextareaHTMLAttributes } from 'react'

type TextBoxInputProps = TextareaHTMLAttributes<HTMLTextAreaElement> & {
  label: string
  error?: string
}

export default function TextBoxInput({
  label,
  error,
  className = '',
  id,
  ...props
}: TextBoxInputProps) {
  return (
    <div className="space-y-2">
      <label htmlFor={id} className="text-sm text-white">
        {label}
      </label>

      <textarea
        id={id}
        rows={5}
        className={`${error ? 'border-red-500' : 'border-white/10'} focus:border-cyan w-full rounded-xl border bg-white/5 px-4 py-3 text-sm text-white outline-none placeholder:text-white/40 ${className}`}
        {...props}
      />
      <p className={`mt-1 min-h-5 text-sm leading-5 ${error ? 'text-red-500' : 'text-transparent'}`}>
        {error ?? ' '}
      </p>
    </div>
  )
}
