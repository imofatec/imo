import ColInputLabel from '../inputs/colinputlabel'
import ColLargeInput from '../inputs/collargeinput'
import ColSelectLabel from '../inputs/colselectlabel'
import { SpinnerButton } from '../spinnerButton'

export default function EditCourseForm({
    courseData,
    formData,
    onChange,
    onSave,
    isLoading
}) {
    return (
        <div className="w-full border-white border rounded-xl p-6 px-10 my-6">
            <ColInputLabel
                label="Nome do Curso"
                placeholder={courseData?.name}
                idInput="name"
                value={formData.name}
                onChange={onChange}
            />

            <ColInputLabel
                label="Categoria"
                placeholder={courseData?.category}
                idInput="category"
                value={formData.category}
                onChange={onChange}
            />

            <ColSelectLabel
                label="Nível"
                placeholder={courseData?.level}
                idInput="level"
                value={formData.level}
                onChange={onChange}
            />

            <ColLargeInput
                placeholder={courseData?.description}
                label="Descrição do Curso"
                idInput="description"
                value={formData.description}
                onChange={onChange}
            />

            <div className='flex flex-row justify-end'>
                <SpinnerButton
                    children="Salvar Alterações"
                    isLoading={isLoading}
                    onClick={onSave}
                    className="bg-custom-header-cyan text-black font-bold px-10"
                />
            </div>
        </div>
    )
}
