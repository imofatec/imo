import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export function InputLabel(props) {
    const { type, id, name, placeholder, label } = props

    return (
        <div className="grid w-full max-w-sm items-center gap-1.5 my-6">
            <Label htmlFor={id}>{label}</Label>
            <Input type={type} id={id} name={name} placeholder={placeholder} className="text-white border bg-custom-blue border-custom-border-gray focus:border-custom-light-blue" />
        </div>
    )
}