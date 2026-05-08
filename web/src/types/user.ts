export type CurrentUserProfile = {
  id: string
  name: string
  email: string
  bio: string | null
  isConfirmed: boolean
  profilePicturePath: string | null
  birthDate: string | null
  availableTimePerDay: string | null
  academicDegree: string | null
  experienceLevel: string | null
  categoriesOfInterest: string[] | null
}

export type UserSummary = {
  id: string
  name: string
  profilePicturePath: string | null
}
