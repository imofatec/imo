import { useState, type ChangeEvent, type InputHTMLAttributes } from 'react'
import CharacterCount from '@/components/ui/CharacterCount'
import PasswordVisibilityButton from '@/components/ui/PasswordVisibilityButton'

type FormInputProps = InputHTMLAttributes<HTMLInputElement> & {
  label?: string
  error?: string
}

export default function FormInput({ label, className = '', id, error, ...props }: FormInputProps) {
  const isPasswordField = props.type === 'password'
  const [showPassword, setShowPassword] = useState(false)
  const [currentLength, setCurrentLength] = useState(() => {
    if (typeof props.value === 'string') return props.value.length
    if (typeof props.defaultValue === 'string') return props.defaultValue.length
    return 0
  })
  const inputType = isPasswordField ? (showPassword ? 'text' : 'password') : props.type
  const activeLength = typeof props.value === 'string' ? props.value.length : currentLength
  const shouldShowCharacterCount =
    typeof props.minLength === 'number' || typeof props.maxLength === 'number'

  function handleChange(event: ChangeEvent<HTMLInputElement>) {
    setCurrentLength(event.target.value.length)
    props.onChange?.(event)
  }

  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      {label && <label htmlFor={id}>{label}</label>}
      <div className="relative">
        <input
          id={id}
          className={`${error ? 'border-red-500' : 'border-white/10'} flex h-10 w-full rounded-md border px-3 py-2 text-sm text-white placeholder:text-white/40 focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${isPasswordField ? 'pr-11' : ''} ${className}`}
          {...props}
          onChange={handleChange}
          type={inputType}
        />
        {isPasswordField && (
          <PasswordVisibilityButton
            visible={showPassword}
            onToggle={() => setShowPassword((current) => !current)}
            disabled={props.disabled}
          />
        )}
      </div>
      <div className="mt-1 flex min-h-5 items-start justify-between gap-3 text-sm leading-5">
        <p className={`${error ? 'text-red-500' : 'text-transparent'} min-h-5`}>{error ?? ' '}</p>
        {shouldShowCharacterCount ? (
          <CharacterCount
            currentLength={activeLength}
            minLength={props.minLength}
            maxLength={props.maxLength}
          />
        ) : null}
      </div>
    </div>
  )
}
