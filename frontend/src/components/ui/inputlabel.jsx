import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export function InputLabel(props) {
    const { type, id, name, placeholder, label, classname } = props

    return (
        <div className="grid w-full max-w-sm items-center gap-1.5">
            <Label htmlFor={id}>{label}</Label>
            <Input type={type} id={id} name={name} placeholder={placeholder} className={classname} />
        </div>
    )
}