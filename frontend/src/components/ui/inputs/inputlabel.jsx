import { Label } from '../label'
import { Input } from './input'

export function InputLabel(props) {
  const { type, id, name, placeholder, defaultValue, label } = props

  return (
    <div className="grid w-full items-center gap-1.5 my-6">
      <Label htmlFor={id}>{label}</Label>
      <Input
        defaultValue={defaultValue}
        type={type}
        id={id}
        name={name}
        placeholder={placeholder}
        className="border bg-custom-blue text-white border-custom-border-gray focus:border-white"
      />
    </div>
  )
}
