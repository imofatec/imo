import LargeInput from './largeinput'

export default function ColLargeInput({ label, placeholder, idInput, value, onChange, error }) {
  return (
    <div className="flex flex-row my-2">
      <div className="flex flex-col w-1/4 justify-center">
        <label className="font-semibold">{label}</label>
      </div>
      <div className="flex flex-col w-3/4">
        <LargeInput
          id={idInput}
          name={idInput}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          className={`${error ? 'border-red-500 focus:border-red-500' : ''}`}
        />
        {error && <p className="text-red-500 text-sm mt-1">{error === 'Required' ? 'Este campo é obrigatório' : error}</p>}
      </div>
    </div>
  )
}
