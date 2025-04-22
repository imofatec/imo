export default function ColSelectLabel({ label, placeholder, idInput, value, onChange, error }) {
    return (
        <div className="flex flex-row my-2">
            <div className="flex flex-col w-1/4">
                <label htmlFor={idInput} className="font-semibold">
                    {label}
                </label>
            </div>
            <div className="flex flex-col w-3/4">
                <select
                    name={idInput}
                    id={idInput}
                    value={value}
                    onChange={onChange}
                    className={`border bg-custom-blue text-white border-custom-border-gray focus:border-white p-2 rounded-md ${error ? 'border-red-500 focus:border-red-500' : ''}`}
                    required
                >
                    <option value="">{placeholder}</option>
                    <option value="iniciante">Iniciante</option>
                    <option value="intermediario">Intermediário</option>
                    <option value="avancado">Avançado</option>
                </select>
                {error && <p className="text-red-500 text-sm mt-1">{error === 'Required' ? 'Este campo é obrigatório' : error}</p>}
            </div>
        </div>
    )
}
