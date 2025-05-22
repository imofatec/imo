export default function LargeInput({ placeholder, id, name, value, onChange, className = '', maxLength = 500}) {
  return (
    <textarea
      id={id}
      name={name}
      className={`w-full rounded-lg my-2 bg-custom-blue p-2 pl-3 text-sm file:font-medium placeholder:text-slate-500 min-h-28 max-h-36 border border-custom-border-gray focus:border-white ${className}`}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      required
      maxLength={maxLength}
    />
  )
}
