import type { SelectHTMLAttributes } from 'react'

type SelectInputProps = SelectHTMLAttributes<HTMLSelectElement> & {
  label: string
  error?: string
  options: { label: string; value: string }[]
  placeholder?: string
}

export default function SelectInput({
  label,
  error,
  options,
  placeholder = 'Selecione uma opção',
  className = '',
  id,
  ...props
}: SelectInputProps) {
  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      <label htmlFor={id} className="text-white">
        {label}
      </label>

      <select
        id={id}
        className={`${error ? 'border-red-500' : 'border-white/10'} [color-scheme:dark] flex h-10 w-full rounded-md border px-3 py-2 text-sm text-white focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${className}`}
        {...props}
      >
        <option value="" disabled hidden style={{ backgroundColor: '#14082f', color: 'white' }}>
          {placeholder}
        </option>

        {options.map((option) => (
          <option
            key={option.value}
            value={option.value}
            style={{ backgroundColor: '#14082f', color: 'white' }}
          >
            {option.label}
          </option>
        ))}
      </select>

      <p className={`mt-1 min-h-5 text-sm leading-5 ${error ? 'text-red-500' : 'text-transparent'}`}>
        {error ?? ' '}
      </p>
    </div>
  )
}
