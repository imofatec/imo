import { useMemo } from 'react'
import { useSkills } from '@/hooks/useSkills'
import type { Skill } from '@/types/skill'

type Props = {
  categorySlug: string
  skillIds?: string[]
}

export default function CourseSkillChips({ categorySlug, skillIds = [] }: Props) {
  const { skills } = useSkills(skillIds.length > 0 ? categorySlug : undefined)
  const courseSkills = useMemo(
    () =>
      skillIds
        .map((skillId) => skills.find((skill) => skill.id === skillId))
        .filter((skill): skill is Skill => Boolean(skill)),
    [skillIds, skills]
  )

  if (courseSkills.length === 0) return null

  return (
    <div className="flex flex-wrap gap-2">
      {courseSkills.map((skill) => (
        <span
          key={skill.id}
          className="bg-cyan/20 text-cyan inline-block max-w-full rounded-full px-3 py-1 text-xs font-medium"
        >
          <span className="block truncate">{skill.name}</span>
        </span>
      ))}
    </div>
  )
}
