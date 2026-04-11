import { useState, type ChangeEvent, type TextareaHTMLAttributes } from 'react'

type TextBoxInputProps = TextareaHTMLAttributes<HTMLTextAreaElement> & {
  label: string
  error?: string
}

export default function TextBoxInput({
  label,
  error,
  className = '',
  id,
  maxLength,
  onChange,
  value,
  defaultValue,
  ...props
}: TextBoxInputProps) {
  const [currentLength, setCurrentLength] = useState(() => {
    if (typeof value === 'string') return value.length
    if (typeof defaultValue === 'string') return defaultValue.length
    return 0
  })

  const activeLength = typeof value === 'string' ? value.length : currentLength

  function handleChange(event: ChangeEvent<HTMLTextAreaElement>) {
    setCurrentLength(event.target.value.length)
    onChange?.(event)
  }

  return (
    <div className="space-y-2">
      <label htmlFor={id} className="text-sm text-white">
        {label}
      </label>

      <textarea
        id={id}
        rows={5}
        maxLength={maxLength}
        value={value}
        defaultValue={defaultValue}
        onChange={handleChange}
        className={`${error ? 'border-red-500' : 'border-white/10'} focus:border-cyan w-full rounded-xl border bg-white/5 px-4 py-3 text-sm text-white outline-none placeholder:text-white/40 ${className}`}
        {...props}
      />

      <div className="mt-1 flex min-h-5 items-start justify-between gap-3 text-sm leading-5">
        <p className={`${error ? 'text-red-500' : 'text-transparent'} min-h-5`}>{error ?? ' '}</p>

        {typeof maxLength === 'number' ? (
          <p className="text-right text-xs text-white/55">{activeLength}/{maxLength}</p>
        ) : null}
      </div>
    </div>
  )
}
