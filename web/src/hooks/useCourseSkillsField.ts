import { useEffect, useRef } from 'react'
import { useWatch, type Control, type UseFormSetValue } from 'react-hook-form'
import { categoryOptions } from '@/constants/courseOptions'
import { useSkills } from '@/hooks/useSkills'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'

type UseCourseSkillsFieldParams = {
  control: Control<CreateCourseData>
  setValue: UseFormSetValue<CreateCourseData>
}

export function useCourseSkillsField({ control, setValue }: UseCourseSkillsFieldParams) {
  const selectedCategory = useWatch({ control, name: 'category' }) ?? ''
  const previousCategoryRef = useRef(selectedCategory)
  const selectedCategorySlug = categoryOptions.find(
    (option) => option.value === selectedCategory
  )?.slug
  const { skills, loading, error } = useSkills(selectedCategorySlug)

  useEffect(() => {
    if (previousCategoryRef.current === selectedCategory) return

    if (!previousCategoryRef.current) {
      previousCategoryRef.current = selectedCategory
      return
    }

    previousCategoryRef.current = selectedCategory
    setValue('skillIds', [], { shouldDirty: true, shouldValidate: true })
  }, [selectedCategory, setValue])

  return {
    skills,
    skillsError: error,
    skillsLoading: loading,
    selectedCategorySlug,
  }
}
