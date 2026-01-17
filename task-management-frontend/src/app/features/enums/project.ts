// CATEGORY
export enum ProjectCategory {
  DESIGN = 'Design',
  DEVELOPMENT = 'Development',
  IT_SERVICES = 'IT Services',
  CONSULTING = 'Consulting',
  MARKETING = 'Marketing',
  OPERATIONS = 'Operations',
  RESEARCH = 'Research'
}

// SUB CATEGORY
export enum DesignSubCategory {
  UI_DESIGN = 'UI Design',
  UX_RESEARCH = 'UX Research',
  BRANDING = 'Branding',
  ILLUSTRATIONS = 'Illustrations',
  PROTOTYPING = 'Prototyping'
}

export enum DevelopmentSubCategory {
  FRONTEND = 'Frontend',
  BACKEND = 'Backend',
  FULL_STACK = 'Full Stack',
  API = 'API Development',
  MOBILE = 'Mobile App'
}

export enum ITServicesSubCategory {
  INFRA = 'Infrastructure',
  CLOUD = 'Cloud Services',
  MAINTENANCE = 'System Maintenance',
  SUPPORT = 'Technical Support',
  SECURITY = 'Security'
}

export enum ConsultingSubCategory {
  BUSINESS = 'Business Analysis',
  PROCESS = 'Process Improvement',
  ADVISORY = 'IT Advisory',
  TRANSFORMATION = 'Digital Transformation',
  STRATEGY = 'Strategy Planning'
}

export enum MarketingSubCategory {
  CONTENT = 'Content Marketing',
  SEO = 'SEO',
  SOCIAL = 'Social Media',
  EMAIL = 'Email Campaigns',
  PERFORMANCE = 'Performance Marketing'
}

export enum OperationsSubCategory {
  PROCESS = 'Process Management',
  QA = 'Quality Assurance',
  VENDOR = 'Vendor Management',
  TOOLS = 'Internal Tools',
  COMPLIANCE = 'Compliance'
}

export enum ResearchSubCategory {
  MARKET = 'Market Research',
  USER = 'User Research',
  POC = 'Proof of Concept',
  DATA = 'Data Analysis',
  INNOVATION = 'Innovation'
}

// TYPE
export enum ProjectType {
  IT_SERVICES = 'IT Services',
  CONSULTING = 'Consulting',
  CRM = 'CRM',
  WEB_APP = 'Web Application',
  MOBILE_APP = 'Mobile Application',
  INTERNAL = 'Internal Tool',
  SUPPORT = 'Support & Maintenance'
}

// PRIORITY
export enum ProjectPriority {
  LOW = 'LOW',
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM'
}

// STATUS
export enum ProjectStatus {
  TO_DO = 'TO DO',
  IN_PROGRESS = 'IN PROGRESS',
  COMPLETED = 'COMPLETED',
  ON_HOLD = 'ON HOLD'
}

export interface ProjectCreationDto {
    name: String;
    type: String;
    priority: String; 
    category: String;
    subCategory: String;
    status: String;
    startDate: String;
    dueDate: String;
}

export interface ProjectDto {
    _id: string;
    projectCode: string;
    name: string;
    type: string;
    priority: string; 
    category: string;
    subCategory: string;
    status: string;
    startDate: string;
    dueDate: string;
}