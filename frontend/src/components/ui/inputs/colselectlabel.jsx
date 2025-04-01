export default function ColSelectLabel({ label, placeholder, idInput }) {
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
                    className="border bg-custom-blue text-white border-custom-border-gray focus:border-white p-2 rounded-md"
                    required
                >
                    <option value="" disabled>
                        {placeholder}
                    </option>
                    <option value="iniciante">Iniciante</option>
                    <option value="intermediario">Intermediário</option>
                    <option value="avancado">Avançado</option>
                </select>
            </div>
        </div>
    )
}