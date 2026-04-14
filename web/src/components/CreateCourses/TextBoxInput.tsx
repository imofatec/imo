import { useState, type ChangeEvent, type TextareaHTMLAttributes } from 'react'
import CharacterCount from '@/components/ui/CharacterCount'

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
  const shouldShowCharacterCount =
    typeof props.minLength === 'number' || typeof maxLength === 'number'

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

        {shouldShowCharacterCount ? (
          <CharacterCount
            currentLength={activeLength}
            minLength={props.minLength}
            maxLength={typeof maxLength === 'number' ? maxLength : undefined}
          />
        ) : null}
      </div>
    </div>
  )
}
