import { InputLabel } from "./inputlabel"

export default function ColInputLabel({ label, idInput, placeholder, value, onChange, error }) {
  return (
    <div className="flex flex-row my-2">
      <div className="flex flex-col w-1/4 justify-center">
        <label className="font-semibold">{label}</label>
      </div>
      <div className="flex flex-col w-3/4">
        <InputLabel
          type="text"
          id={idInput}
          name={idInput}
          placeholder={placeholder}
          label={label}
          value={value}
          onChange={onChange}
          className={`${error ? 'border-red-500 focus:border-red-500' : ''}`}
        />
        {error && <p className="text-red-500 text-sm !mt-0">{error === 'Required' ? 'Este campo é obrigatório' : error}</p>}
      </div>
    </div>
  )
}
