import { Label } from '../label'
import { Input } from './input'

export function InputLabel(props) {
  const { type, id, name, placeholder, label, onChange, value,className= '' } = props

  return (
    <div className="grid w-full items-center gap-1.5 my-2">
      <Label htmlFor={id}>{label}</Label>
      <Input
        type={type}
        id={id}
        name={name}
        placeholder={placeholder}
        onChange={onChange}
        value={value}
        className={`border bg-custom-blue text-white border-custom-border-gray focus:border-white ${className}`}
      />
    </div>
  )
}
