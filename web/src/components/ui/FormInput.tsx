import { useState, type InputHTMLAttributes } from 'react'
import PasswordVisibilityButton from '@/components/ui/PasswordVisibilityButton'

type FormInputProps = InputHTMLAttributes<HTMLInputElement> & {
  label?: string
  error?: string
}

export default function FormInput({ label, className = '', id, error, ...props }: FormInputProps) {
  const isPasswordField = props.type === 'password'
  const [showPassword, setShowPassword] = useState(false)
  const inputType = isPasswordField ? (showPassword ? 'text' : 'password') : props.type

  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      {label && <label htmlFor={id}>{label}</label>}
      <div className="relative">
        <input
          id={id}
          className={`${error ? 'border-red-500' : 'border-white/10'} flex h-10 w-full rounded-md border px-3 py-2 text-sm text-white placeholder:text-white/40 focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${isPasswordField ? 'pr-11' : ''} ${className}`}
          {...props}
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
      <p className={`mt-1 min-h-5 text-sm leading-5 ${error ? 'text-red-500' : 'text-transparent'}`}>
        {error ?? ' '}
      </p>
    </div>
  )
}
