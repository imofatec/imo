type DividerProps = {
  text: string
}

export default function Divider({ text }: DividerProps) {
  return (
    <div className="flex h-8 items-center justify-center">
      <div className="bg-custom-border-gray h-px w-12"></div>
      <p className="text-custom-text-gray px-4">{text}</p>
      <div className="bg-custom-border-gray h-px w-12"></div>
    </div>
  )
}
