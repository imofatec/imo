import { expect, type APIRequestContext } from '@playwright/test'
import { categoryOptions } from '../../src/constants/courseOptions'
import { extractPaginatedArray } from '../../src/lib/pagination'
import type { Category } from '../../src/types/category'
import type { Course, CourseDetails } from '../../src/types/course'
import type { Skill } from '../../src/types/skill'
import type { AuthSession } from './auth'
import { apiBaseUrl } from './auth'
import type { CourseInput } from './course-data'

type SearchCoursesParams = {
  matchType?: 'PERFECT' | 'CONTAINS' | 'STARTS_WITH'
  combineWith?: 'AND' | 'OR'
  page?: number
  size?: number
  categorySlug?: string
  name?: string
  contributorId?: string
}

function authHeaders(accessToken: string) {
  return {
    Authorization: `Bearer ${accessToken}`,
  }
}

function extractCourseId(payload: unknown): string | null {
  if (!payload || typeof payload !== 'object') return null

  const record = payload as Record<string, unknown>

  if (typeof record.id === 'string') return record.id

  if (record.course && typeof record.course === 'object') {
    const course = record.course as Record<string, unknown>
    if (typeof course.id === 'string') return course.id
  }

  return null
}

function getCategorySlug(categoryValue: string) {
  return categoryOptions.find((category) => category.value === categoryValue)?.slug ?? categoryValue
}

export async function getCategoriesByApi(request: APIRequestContext, accessToken: string) {
  const response = await request.get(`${apiBaseUrl}/api/course/categories`, {
    headers: authHeaders(accessToken),
  })

  expect(response.ok()).toBeTruthy()

  return (await response.json()) as Category[]
}

export async function getSkillsByCategoryApi(
  request: APIRequestContext,
  accessToken: string,
  categorySlug: string
) {
  const response = await request.get(`${apiBaseUrl}/api/skill/${categorySlug}`, {
    headers: authHeaders(accessToken),
  })

  expect(response.ok()).toBeTruthy()

  return (await response.json()) as Skill[]
}

export async function searchCoursesByApi(
  request: APIRequestContext,
  accessToken: string,
  params: SearchCoursesParams = {}
) {
  const query = new URLSearchParams({
    matchType: params.matchType ?? 'PERFECT',
    combineWith: params.combineWith ?? 'AND',
    page: String(params.page ?? 0),
    size: String(params.size ?? 20),
    ...(params.categorySlug ? { categorySlug: params.categorySlug } : {}),
    ...(params.name ? { name: params.name } : {}),
    ...(params.contributorId ? { contributorId: params.contributorId } : {}),
  }).toString()

  const response = await request.get(`${apiBaseUrl}/api/course/search?${query}`, {
    headers: authHeaders(accessToken),
  })

  expect(response.ok()).toBeTruthy()

  const payload = (await response.json()) as unknown
  const { data } = extractPaginatedArray<Course>(payload)

  return data
}

export async function getCourseDetailsByApi(
  request: APIRequestContext,
  accessToken: string,
  courseId: string
) {
  const response = await request.get(`${apiBaseUrl}/api/course/details/${courseId}`, {
    headers: authHeaders(accessToken),
  })

  expect(response.ok()).toBeTruthy()

  return (await response.json()) as CourseDetails
}

export async function getCourseDetailsResponseByApi(
  request: APIRequestContext,
  accessToken: string,
  courseId: string
) {
  return request.get(`${apiBaseUrl}/api/course/details/${courseId}`, {
    headers: authHeaders(accessToken),
  })
}

export async function createCourseByApi(
  request: APIRequestContext,
  session: AuthSession,
  course: CourseInput
) {
  const skillIds =
    course.skillIds.length > 0
      ? course.skillIds
      : (
          await getSkillsByCategoryApi(
            request,
            session.accessToken,
            getCategorySlug(course.category)
          )
        )
          .slice(0, 1)
          .map((skill) => skill.id)

  expect(skillIds.length).toBeGreaterThan(0)

  const response = await request.post(`${apiBaseUrl}/api/course`, {
    headers: authHeaders(session.accessToken),
    data: {
      ...course,
      skillIds,
      contributorId: session.id,
    },
  })

  expect(response.ok()).toBeTruthy()

  let createdCourseId: string | null = null

  try {
    const payload = (await response.json()) as unknown
    createdCourseId = extractCourseId(payload)
  } catch {
    createdCourseId = null
  }

  if (!createdCourseId) {
    const courses = await searchCoursesByApi(request, session.accessToken, {
      contributorId: session.id,
      name: course.name,
      matchType: 'PERFECT',
    })

    createdCourseId = courses.find((item) => item.name.name === course.name)?.id ?? null
  }

  expect(createdCourseId).toBeTruthy()

  return getCourseDetailsByApi(request, session.accessToken, createdCourseId!)
}

export async function markLessonAsWatchedByApi(
  request: APIRequestContext,
  accessToken: string,
  lessonId: string
) {
  const response = await request.put(`${apiBaseUrl}/api/progress/${lessonId}`, {
    headers: authHeaders(accessToken),
  })

  expect(response.ok()).toBeTruthy()
}
