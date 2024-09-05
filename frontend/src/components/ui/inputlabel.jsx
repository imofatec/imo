import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export function InputLabel(props) {
    const { type, id, name, placeholder, label } = props

    return (
        <div className="grid w-full max-w-sm items-center gap-1.5">
            <Label htmlFor={id}>{label}</Label>
            <Input type={type} id={id} name={name} placeholder={placeholder} className="border bg-custom-blue text-white border-custom-border-gray focus:border-white" />
        </div>
    )
}