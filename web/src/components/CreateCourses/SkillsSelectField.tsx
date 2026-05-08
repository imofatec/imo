import { X } from 'lucide-react'
import type { ChangeEvent } from 'react'
import { useController, type Control } from 'react-hook-form'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'
import type { Skill } from '@/types/skill'

type SkillsSelectFieldProps = {
  control: Control<CreateCourseData>
  error?: string
  hasCategorySelected: boolean
  loading?: boolean
  skills: Skill[]
}

export default function SkillsSelectField({
  control,
  error,
  hasCategorySelected,
  loading = false,
  skills,
}: SkillsSelectFieldProps) {
  const { field } = useController({
    control,
    name: 'skillIds',
  })
  const selectedSkillIds = field.value ?? []
  const selectedSkills = selectedSkillIds
    .map((skillId) => skills.find((skill) => skill.id === skillId))
    .filter((skill): skill is Skill => Boolean(skill))
  const availableSkills = skills.filter((skill) => !selectedSkillIds.includes(skill.id))
  const isDisabled =
    !hasCategorySelected || loading || selectedSkillIds.length >= 2 || availableSkills.length === 0
  const placeholder =
    selectedSkillIds.length >= 2
      ? 'Limite de skills atingido'
      : !hasCategorySelected
        ? 'Selecione uma categoria primeiro'
        : loading
          ? 'Carregando skills...'
          : availableSkills.length === 0
            ? 'Nenhuma skill disponível'
            : 'Selecione as skills'

  function addSkill(event: ChangeEvent<HTMLSelectElement>) {
    const skillId = event.target.value
    event.target.value = ''

    if (!skillId || selectedSkillIds.includes(skillId) || selectedSkillIds.length >= 2) return

    field.onChange([...selectedSkillIds, skillId])
  }

  function removeSkill(skillId: string) {
    field.onChange(selectedSkillIds.filter((selectedSkillId) => selectedSkillId !== skillId))
  }

  return (
    <div className="my-2 grid w-full items-center gap-1.5">
      <label htmlFor="skillIds" className="text-white">
        Skills
      </label>

      {selectedSkills.length > 0 ? (
        <div className="flex min-h-10 flex-wrap gap-2">
          {selectedSkills.map((skill) => (
            <span
              key={skill.id}
              className="border-cyan/30 bg-cyan/10 text-cyan inline-flex max-w-full items-center gap-2 rounded-full border px-3 py-1.5 text-sm"
            >
              <span className="truncate">{skill.name}</span>
              <button
                type="button"
                onClick={() => removeSkill(skill.id)}
                aria-label={`Remover skill ${skill.name}`}
                className="hover:bg-cyan/15 text-cyan/80 hover:text-cyan inline-flex h-5 w-5 shrink-0 items-center justify-center rounded-full transition"
              >
                <X size={14} />
              </button>
            </span>
          ))}
        </div>
      ) : null}

      <select
        id="skillIds"
        name={field.name}
        value=""
        onBlur={field.onBlur}
        onChange={addSkill}
        disabled={isDisabled}
        className={`${error ? 'border-red-500' : 'border-white/10'} flex h-12 w-full rounded-xl border bg-white/5 px-3 py-2 text-sm text-white scheme-dark focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50`}
      >
        <option value="" disabled hidden style={{ backgroundColor: '#14082f', color: 'white' }}>
          {placeholder}
        </option>

        {availableSkills.map((skill) => (
          <option
            key={skill.id}
            value={skill.id}
            style={{ backgroundColor: '#14082f', color: 'white' }}
          >
            {skill.name}
          </option>
        ))}
      </select>

      <p
        className={`mt-1 min-h-5 text-sm leading-5 ${error ? 'text-red-500' : 'text-transparent'}`}
      >
        {error ?? ' '}
      </p>
    </div>
  )
}
