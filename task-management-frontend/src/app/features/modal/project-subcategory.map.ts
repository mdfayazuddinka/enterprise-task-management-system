import {
  ProjectCategory,
  DesignSubCategory,
  DevelopmentSubCategory,
  ITServicesSubCategory,
  ConsultingSubCategory,
  MarketingSubCategory,
  OperationsSubCategory,
  ResearchSubCategory
} from '../enums/project';

export const PROJECT_SUBCATEGORY_MAP: Record<ProjectCategory, string[]> = {
  [ProjectCategory.DESIGN]: Object.values(DesignSubCategory),
  [ProjectCategory.DEVELOPMENT]: Object.values(DevelopmentSubCategory),
  [ProjectCategory.IT_SERVICES]: Object.values(ITServicesSubCategory),
  [ProjectCategory.CONSULTING]: Object.values(ConsultingSubCategory),
  [ProjectCategory.MARKETING]: Object.values(MarketingSubCategory),
  [ProjectCategory.OPERATIONS]: Object.values(OperationsSubCategory),
  [ProjectCategory.RESEARCH]: Object.values(ResearchSubCategory)
};
